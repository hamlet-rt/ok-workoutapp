package com.github.hamlet_rt.workoutapp.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to WrkContext")
