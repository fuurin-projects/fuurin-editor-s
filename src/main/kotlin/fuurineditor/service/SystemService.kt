package fuurineditor.service

import org.springframework.stereotype.Service

@Service
class SystemService {

    fun getVersion(): String {
        return "0.0.1"
    }

}