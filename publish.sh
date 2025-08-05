#!/bin/bash

set -e
VERSION_NAME=$(grep 'VERSION_NAME=' gradle.properties | cut -d'=' -f2)
MODULE_NAME=segmentifyandroidsdk
DEPLOY_DIR=$MODULE_NAME/build/deployment-package
BUNDLE_NAME=android-$VERSION_NAME-bundle.zip
MAVEN_REPO=$HOME/.m2/repository/com/segmentify/sdk/android/$VERSION_NAME

CYAN='\033[1;36m'
GREEN='\033[1;32m'
YELLOW='\033[1;33m'
RED='\033[1;31m'
NC='\033[0m'

function info() {
  echo -e "${CYAN}📦 $1${NC}"
}

function success() {
  echo -e "${GREEN}✅ $1${NC}"
}

function warn() {
  echo -e "${YELLOW}⚠️  $1${NC}"
}

function error() {
  echo -e "${RED}🛑 $1${NC}" && exit 1
}

# GPG configurations from gradle.properties
SIGNING_KEY_ID=$(grep 'signing.keyId=' gradle.properties | cut -d'=' -f2)
SIGNING_PASSWORD=$(grep 'signing.password=' gradle.properties | cut -d'=' -f2)
SIGNING_SECRET_KEY_RING_FILE=$(grep 'signing.secretKeyRingFile=' gradle.properties | cut -d'=' -f2)

# Gradle environment properties
export ORG_GRADLE_PROJECT_signingKeyId="$SIGNING_KEY_ID"
export ORG_GRADLE_PROJECT_signingPassword="$SIGNING_PASSWORD"
export ORG_GRADLE_PROJECT_signingSecretKeyRingFile="$SIGNING_SECRET_KEY_RING_FILE"

# GPG key ID
GPG_KEY_ID=${SIGNING_KEY_ID:-$(gpg --list-secret-keys --keyid-format=long | grep '^sec' | awk '{print $2}' | cut -d'/' -f2 | head -n 1)}
export GPG_TTY=$(tty)

info "Gradle full publish işlemi başlatılıyor..."
./gradlew clean :$MODULE_NAME:assembleRelease :$MODULE_NAME:androidSourcesJar :$MODULE_NAME:androidJavadocsJar :$MODULE_NAME:publishMavenAndroidPublicationToMavenLocal || error "Gradle işlemi başarısız oldu."

# Create temp directory
TMP_BUNDLE_DIR="$MODULE_NAME/build/tmp-publish-dir"
rm -rf "$TMP_BUNDLE_DIR"
mkdir -p "$TMP_BUNDLE_DIR"

# Export files to here
FILES=(
  android-$VERSION_NAME.aar
  android-$VERSION_NAME.pom
  android-$VERSION_NAME-sources.jar
  android-$VERSION_NAME-javadoc.jar
)

for file in "${FILES[@]}"
do
  if [ -f "$MAVEN_REPO/$file" ]; then
    cp "$MAVEN_REPO/$file" "$TMP_BUNDLE_DIR" && success "$file copied."

    # GPG sign
    if gpg --batch --yes --armor --detach-sign --local-user "$GPG_KEY_ID" "$TMP_BUNDLE_DIR/$file"; then
      success "$file signed."
    else
      error "$file cannot be signed. Check GPG configurations."
    fi

    # MD5 ve SHA1
    (cd "$TMP_BUNDLE_DIR" && md5sum "$file" | awk '{print $1}' > "$file.md5") && success "$file.md5 generated."
    (cd "$TMP_BUNDLE_DIR" && sha1sum "$file" | awk '{print $1}' > "$file.sha1") && success "$file.sha1 generated."
  else
    warn "File cannot be found, ignoring: $file"
  fi
  echo

done

# Create directory structure for Maven bundle
TARGET_BUNDLE_DIR="$TMP_BUNDLE_DIR/com/segmentify/sdk/android/$VERSION_NAME"
mkdir -p "$TARGET_BUNDLE_DIR"

for item in "$TMP_BUNDLE_DIR"/*; do
  if [[ "$item" != "$TARGET_BUNDLE_DIR"* && -f "$item" ]]; then
    mv "$item" "$TARGET_BUNDLE_DIR" || error "$item cannot be moved."
  fi
done

# ZIP directory creation in case not present
mkdir -p "$DEPLOY_DIR"
DEPLOY_DIR_ABS=$(cd "$DEPLOY_DIR"; pwd)

# ZIP generation
cd "$TMP_BUNDLE_DIR" || error "TMP directory problem"
zip -r "$BUNDLE_NAME" . || error "ZIP cannot be generated."
mv "$BUNDLE_NAME" "$DEPLOY_DIR_ABS/$BUNDLE_NAME" || error "ZIP file cannot be moved."
rm -rf "$TMP_BUNDLE_DIR"

# ZIP check
if [ -f "$DEPLOY_DIR_ABS/$BUNDLE_NAME" ]; then
  success "ZIP package created successfully: $DEPLOY_DIR_ABS/$BUNDLE_NAME"
else
  warn "ZIP file cannot be found: $BUNDLE_NAME"
fi

# Bilgilendirme
success "🎉 Ready for publish, package and signs are created."
echo -e "📁 Directory: $DEPLOY_DIR"
echo -e "📤 Manuel upload: https://central.sonatype.com/"