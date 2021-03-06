plugins {
	kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

repositories {
	mavenCentral()
    jcenter()
}

tasks.withType<org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest> {
    useJUnitPlatform()
}


kotlin {
    val npmVersion: String by project
    val jupiterVersion: String by project

	/* Targets configuration omitted.
	*  To find out how to configure the targets, please follow the link:
	*  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    js {
        browser {}
        nodejs {}
    }

	jvm {
		withJava()
	}

	sourceSets {
        val coroutineVersion: String by project

		val commonMain by getting {
			dependencies {
				implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
			}
		}
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))

			}
		}

		val jsMain by getting {
			dependencies {
                implementation(kotlin("stdlib-js"))
                implementation(npm("is-sorted", "$npmVersion"))
			}
		}

		val jsTest by getting {
			dependencies {
				implementation(kotlin("test-js"))
			}
		}

		val jvmMain by getting {
			dependencies {
                implementation(project(":ok-remindercalendar-common-be"))
				implementation(kotlin("stdlib"))
			}
		}

		val jvmTest by getting {
			dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")

			}
		}
//        val jvmMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test-junit"))
//            }
//        }
	}
}