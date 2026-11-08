cmake_minimum_required(VERSION 3.22)
project(BreakingCraftOverlay LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(BreakingCraftOverlay SHARED
    src/common/transition.cpp
    src/zoom/zoom.cpp
    src/counter/fps.cpp
)

target_include_directories(BreakingCraftOverlay PRIVATE 
    src
    ${CMAKE_CURRENT_SOURCE_DIR}/../preloader/src
)

find_library(log-lib log)
find_library(egl-lib EGL)

target_link_libraries(BreakingCraftOverlay
    PRIVATE
    preloader
    ${log-lib}
    ${egl-lib}
)