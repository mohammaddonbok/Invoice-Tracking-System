

package com.example.training.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedOrigins("http://localhost:4200")
//                .allowedHeaders("Content-Type")
//                .allowedHeaders("x-xsrf-token")
//                .allowedHeaders("Authorization")
//                .allowedHeaders("Access-Control-Allow-Headers")
//                .allowedHeaders("Origin")
//                .allowedHeaders("Accept")
//                .allowedHeaders("X-Requested-With")
//                .allowedHeaders("Access-Control-Request-Method")
//                .allowedHeaders("Access-Control-Request-Headers")
//                .exposedHeaders("Authorization");
                registry.addMapping("/api/createUser").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/logIn").allowedMethods("GET", "POST", "PUT","OPTIONS").allowedOrigins("*");
                registry.addMapping("/api/displayUsers").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/api/invoices").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/api/OwnerInvoices").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/api/createInvoices").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/api/pageAble").allowedMethods("GET", "POST", "PUT");
                registry.addMapping("/api/invoice/*").allowedMethods("GET", "POST", "PUT","DELETE");
                registry.addMapping("/api/user/*").allowedMethods("GET", "POST", "PUT","DELETE");
                registry.addMapping("/api/user/*/*").allowedMethods("GET", "POST", "PUT","DELETE");

            }
        }

    //        @Bean
//        public WebMvcConfigurer corsConfigurer() {
//                return new WebMvcConfigurer() {
//                        @Override
//                        public void addCorsMappings(CorsRegistry registry) {
//                                registry
//                                        .addMapping("api/**")
//                                        .allowedMethods("POST", "GET", "PUT", "DELETE","OPTIONS")
//                                        .allowedHeaders("*")
//                                        .exposedHeaders("*")
//                                        .allowedOrigins("http://localhost:4200/");
//
//                        }
//                };
//        }
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                System.out.println("hiii");
//                registry.addMapping("**").allowedMethods("GET", "POST", "PUT", "OPTIONS")
//                        .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
//                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                        .allowCredentials(false).maxAge(3600);
//
//                registry.addMapping("/api/logIn").allowedMethods("GET", "POST", "PUT", "OPTIONS")
//                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
//                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                .allowedOrigins("*");
//                                registry.addMapping("/api/v1/invoices").allowedMethods("GET", "POST", "PUT");
//                                registry.addMapping("/invoices").allowedMethods("GET", "POST", "PUT");
//                                registry.addMapping("/invoices/").allowedMethods("GET", "POST", "PUT");
//                                registry.addMapping("/api/v1/**").allowedMethods("GET", "POST", "PUT");
//                                registry.addMapping("/uploadfile").allowedMethods("GET", "POST", "PUT");
//                                registry.addMapping("/uploadfile/form1").allowedMethods("GET", "POST", "PUT");
//            }
//        };
//    }
//
//}

//
//registry.addMapping("/**").allowedMethods("GET", "POST", "OPTIONS", "PUT")
//        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//        "Access-Control-Request-Headers")
//        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//        .allowCredentials(false).maxAge(3600);