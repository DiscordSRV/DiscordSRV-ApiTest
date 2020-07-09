# DiscordSRV-ApiTest
Example usage of DiscordSRV's API

# Maven
```
<repository>
    <id>Scarsz-Nexus</id>
    <url>https://nexus.scarsz.me/content/groups/public/</url>
</repository>

...

<dependency>
    <groupId>com.discordsrv</groupId>
    <artifactId>discordsrv</artifactId>
    <version>1.19.1</version>
    <scope>provided</scope>
</dependency>
```

# Gradle
```groovy
repositories {
    maven { url 'https://nexus.scarsz.me/content/groups/public/' }
}

dependencies {
    compileOnly 'com.discordsrv:discordsrv:1.19.1'
}
```
