Releasing
========

 1. Bump the VERSION_NAME property in `gradle.properties` based on Major.Minor.Patch naming scheme
 2. Update `CHANGELOG.md` for the impending release.
 3. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the version you set in step 1)
 4. `git tag -a X.Y.X -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 5. `./gradlew clean uploadArchives`
 6. `git push && git push --tags`
 7. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.