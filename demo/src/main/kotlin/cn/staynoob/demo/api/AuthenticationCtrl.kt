package cn.staynoob.demo.api

import cn.staynoob.demo.domain.Principal
import cn.staynoob.springsecurityjwt.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/authenticate"], produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
class AuthenticationCtrl {

    @Autowired
    private lateinit var jwtService: JwtService

//    @Autowired
//    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping
    fun create(@Valid @RequestBody principal: Principal): ResponseEntity<*> {
        // your own authenticate logic ex:
//        val upToken = UsernamePasswordAuthenticationToken(principalDTO.username, principalDTO.password)
//        val auth = authenticationManager.authenticate(upToken)
//        SecurityContextHolder.getContext().authentication = auth
        val token = jwtService.createToken(principal)
        return ResponseEntity.ok("{\"token\":\"$token\"}")
    }
}