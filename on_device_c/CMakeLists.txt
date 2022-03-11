cmake_minimum_required(VERSION 3.0)
project(face_me)

set(CMAKE_MODULE_PATH "${CMAKE_CURRENT_SOURCE_DIR}/CMake;${CMAKE_MODULE_PATH}")
include(Utilities)

set(CMAKE_CXX_STANDARD 17)

set(DEPENDENCY_DOWNLOAD_PATH ${CMAKE_CURRENT_SOURCE_DIR}/dependency)
if(NOT EXISTS ${DEPENDENCY_DOWNLOAD_PATH})
  file(MAKE_DIRECTORY ${DEPENDENCY_DOWNLOAD_PATH})
endif()

fetch_repo(KinesisVideoProducer)
add_subdirectory(${DEPENDENCY_DOWNLOAD_PATH}/libKinesisVideoProducer/KinesisVideoProducer-src EXCLUDE_FROM_ALL)

find_library(GPIOD_LIBRARY NAMES libgpiod.so)
if(NOT GPIOD_LIBRARY)
    message(FATAL_ERROR "gpiod library not found. Install apt install libgpiod-dev")
endif()

##### Build Targets #####
file(GLOB APPLICATION_CPP_SOURCE_FILES "src/*.cpp")

include_directories(${KinesisVideoProducer_SOURCE_DIR}/src/)

add_executable(${PROJECT_NAME} APPLICATION_CPP_SOURCE_FILES)
add_dependencies(${PROJECT_NAME} libKinesisVideoProducer)

target_link_libraries(${PROJECT_NAME} PUBLIC libKinesisVideoProducer ${GPIOD_LIBRARY})
