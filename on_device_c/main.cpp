/*
 * https://github.com/awslabs/amazon-kinesis-video-streams-producer-sdk-cpp/tree/master/samples
 * Frankenstein code from here and from libgpiod's manual
 *
 * Basic structure will be one of two thigns:
 *  Either, monitor gstreamer stream for motion (maybe not possible)
 *  Or, setup a callback function on libgpiod to record and stream to kinesis (since we don't want an infinite stream)
 */

#include <iostream>

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
