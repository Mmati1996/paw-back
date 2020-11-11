
package com.example.back.config;

@SuppressWarnings("deprecation")

public class SecurityConfig{ //extends WebSecurityConfigurerAdapter {

    //based on role
    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    //    auth.inMemoryAuthentication().withUser("MateuszDukat").password("password").roles("ADMIN");
    //    auth.inMemoryAuthentication().withUser("MDuk").password("password").roles("USER");
    //}


    //@Override
    //protected void configure(HttpSecurity http) throws Exception{
    //    http.cors();
    //    http.csrf().disable();
    //    http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
    //}

    //for registration
    //based on URL
    //@Override
    //protected void configure(HttpSecurity http) throws Exception{
    //    http.csrf().disable();
    //    http.authorizeRequests().antMatchers("**/adduser").fullyAuthenticated().and().httpBasic();
    //}



    //@Bean
    //public static NoOpPasswordEncoder passwordEncoder(){
    //    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    //}
}

