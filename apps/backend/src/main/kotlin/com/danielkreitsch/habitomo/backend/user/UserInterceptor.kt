package com.danielkreitsch.habitomo.backend.user

import jakarta.servlet.http.*
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor

class UserInterceptor : HandlerInterceptor {
  private val logger = LoggerFactory.getLogger(javaClass)

  override fun preHandle(
      request: HttpServletRequest,
      response: HttpServletResponse,
      handler: Any
  ): Boolean {
    val authorizationHeaderValue = request.getHeader("Authorization")
    if (authorizationHeaderValue != null) {
      val userId = authorizationHeaderValue.toInt()
      request.setAttribute("user", User(userId))
    } else {
      this.logger.debug("No user id passed: " + request.requestURL)
    }
    return true
  }
}
