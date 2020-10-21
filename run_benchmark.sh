#!/bin/bash

./gradlew clean
rm -rf results

mkdir -p results/control/debug results/control/release
./gradlew benchmark-control:connectedCheck
find benchmark-control/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/control/debug
./gradlew benchmark-control:connectedCheck -DenableR8=true
find benchmark-control/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/control/release

mkdir -p results/coil/debug results/coil/release
./gradlew benchmark-coil:connectedCheck
find benchmark-coil/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/coil/debug
./gradlew benchmark-coil:connectedCheck -DenableR8=true
find benchmark-coil/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/coil/release

mkdir -p results/fresco-java/debug results/fresco-java/release
./gradlew benchmark-fresco:connectedCheck
find benchmark-fresco/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/fresco-java/debug
./gradlew benchmark-fresco:connectedCheck -DenableR8=true
find benchmark-fresco/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/fresco-java/release

mkdir -p results/fresco-native/debug results/fresco-native/release
./gradlew benchmark-fresco:connectedCheck -DenableNativeCode=true
find benchmark-fresco/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/fresco-native/debug
./gradlew benchmark-fresco:connectedCheck -DenableNativeCode=true -DenableR8=true
find benchmark-fresco/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/fresco-native/release

mkdir -p results/glide/debug results/glide/release
./gradlew benchmark-glide:connectedCheck
find benchmark-glide/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/glide/debug
./gradlew benchmark-glide:connectedCheck -DenableR8=true
find benchmark-glide/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/glide/release

mkdir -p results/picasso-2/debug results/picasso-2/release
./gradlew benchmark-picasso-2:connectedCheck
find benchmark-picasso-2/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/picasso-2/debug
./gradlew benchmark-picasso-2:connectedCheck -DenableR8=true
find benchmark-picasso-2/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/picasso-2/release

mkdir -p results/picasso-3/debug results/picasso-3/release
./gradlew benchmark-picasso-3:connectedCheck
find benchmark-picasso-3/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/picasso-3/debug
./gradlew benchmark-picasso-3:connectedCheck -DenableR8=true
find benchmark-picasso-3/build/outputs/connected_android_test_additional_output -type f -print0 | sed 's/ /\\ /g' | xargs sh -c 'cp "$@" "$0"' results/picasso-3/release
