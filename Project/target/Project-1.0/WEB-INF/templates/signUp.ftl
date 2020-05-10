<#import "header.ftl" as c/>
<#import "spring.ftl" as spring />
<@c.page title="Sign Up">

    <div class="main">
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="signup-form">
                        <h2 class="form-title"><@spring.message 'form.sign.up'/></h2>
                        <@spring.bind "signUpDto"/>
                        <form method="POST" class="register-form" id="register-form">
                            <div class="form-group">
<#--                                <input type="text" name="username" id="username" placeholder="<@spring.message 'form.username'/>"/>-->
                                <label for="username"><@spring.message 'form.username'/></label><br/>
                                <@spring.formInput "signUpDto.username"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group">
<#--                                <input type="email" name="email" id="email" placeholder="<@spring.message 'form.email'/>"/>-->
                                <label for="email"><@spring.message 'form.email'/></label><br/>
                                <@spring.formInput "signUpDto.email"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group">
<#--                                <input type="password" name="password" id="password" placeholder="<@spring.message 'form.password'/>"/>-->
                                <label for="email"><@spring.message 'form.password'/></label><br/>
                                <@spring.formInput "signUpDto.password"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signup" id="signup" class="form-submit" value="<@spring.message 'form.sign.up.button'/>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
</@c.page>