<template>
  <div class="register">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <h3 class="title">诗词交流平台</h3>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" auto-complete="off" placeholder="账号" type="text">
          <svg-icon slot="prefix" class="el-input__icon input-icon" icon-class="user"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="registerForm.password" auto-complete="off" placeholder="密码" type="password" @keyup.enter.native="handleRegister">
          <svg-icon slot="prefix" class="el-input__icon input-icon" icon-class="password"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input v-model="registerForm.confirmPassword" auto-complete="off" placeholder="确认密码" type="password" @keyup.enter.native="handleRegister">
          <svg-icon slot="prefix" class="el-input__icon input-icon" icon-class="password"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" auto-complete="off" placeholder="手机号" style="width: 63%" @keyup.enter.native="handleRegister">
          <svg-icon slot="prefix" class="el-input__icon input-icon" icon-class="phone"/>
        </el-input>
        <div class="register-code">
          <el-button plain @click="getCode">获取验证码</el-button>
        </div>
      </el-form-item>
      <el-form-item v-if="captchaEnabled" prop="code">
        <el-input v-model="registerForm.code" auto-complete="off" placeholder="验证码" @keyup.enter.native="handleRegister">
          <svg-icon slot="prefix" class="el-input__icon input-icon" icon-class="validCode"/>
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          style="width:100%;"
          type="primary"
          @click.native.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;">
          <router-link :to="'/login'" class="link-type">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {getPhoneCodeImg, register} from "@/api/login";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeUrl: '',
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        phone: '',
        code: '',
        uuid: ''
      },
      registerRules: {
        username: [
          {required: true, trigger: "blur", message: "请输入您的账号"},
          {min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
          {min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur"},
          {pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur"}
        ],
        confirmPassword: [
          {required: true, trigger: "blur", message: "请再次输入您的密码"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ],
        phone: [
          {required: true, trigger: 'blur', message: "请输入您的手机号"},
          {pattern: /^1[3|456789][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ],
        code: [{required: true, trigger: "change", message: "请输入验证码"}]
      },
      loading: false,
      captchaEnabled: true
    };
  },
  methods: {
    getCode() {
      this.$refs.registerForm.validateField('phone', (valid) => {
        if (!valid) {
          getPhoneCodeImg(this.registerForm.phone).then(res => {
            this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
            if (res.code === 200) {
              this.$message.success(res.msg)
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true
          register(this.registerForm).then(res => {
            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => {
            });
          }).catch(() => {
            this.loading = false
          })
        }
      });
    }
  }
};
</script>

<style lang="scss" rel="stylesheet/scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.png");
  background-size: cover;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: rgba(102, 204, 255, 0.4);
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.register-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }

  & > .el-button {
    height: 38px;
  }
}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
