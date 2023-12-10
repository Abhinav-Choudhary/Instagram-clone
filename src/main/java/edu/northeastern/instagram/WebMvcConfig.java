// package edu.northeastern.instagram;

// import java.util.logging.Level;
// import java.util.logging.Logger;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import edu.northeastern.controller.GuestController;

// @Configuration
// public class WebMvcConfig implements WebMvcConfigurer {

//     private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
//     {
//         "classpath:/META-INF/resources/",
// 		 "classpath:/resources/",
//         "classpath:/static/", 
// 		"classpath:/public/",
// 		"classpath:/custom/",
// 		"file:/opt/myfiles/"
//     };
    
//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         try {
//             String path="file:///D:/Northeastern/Courses/ESD/instagram/src/main/resources/static/posts/";
//             registry.addResourceHandler("/static/**")
//                     .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//         } catch(Exception e) {
//             Logger.getLogger(WebMvcConfig.class.getName()).log(Level.SEVERE, null, e);
//         }
        
//     }
// }
