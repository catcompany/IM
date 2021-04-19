cmake_minimum_required(VERSION 3.4.1)

project(imorning_im)


add_library(main SHARED main.cpp)

target_link_libraries( main )