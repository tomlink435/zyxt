<template>
  <div class="navbar">

    <nav>
      <ul>
        <li><router-link to="/">首页</router-link></li>
        <li><router-link to="/data-resources">数据资源</router-link></li>
        <li><router-link to="/physical-resources">实物资源</router-link></li>
        <li><router-link to="/physical-resources">服务案例</router-link></li>
        <li><router-link to="/physical-resources">服务成效</router-link></li>
        <li><router-link to="/physical-resources">标准规范</router-link></li>
        <!-- Add other links as needed -->
      </ul>
    </nav>
    <div class="auth-links">
      <router-link to="/physical-resources">关于我们</router-link>
      <router-link to="/register">注册</router-link> | <router-link to="/login">登录</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navbar'
}
</script>

<style scoped>
.navbar {
  display: flex;
  align-items: center;
  background-color: #337ab7;
  padding: 10px 20px;
}

.logo img {
  height: 60px;
  margin-right: 20px;
}

nav ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

nav li {
  margin-right: 20px;
}

nav a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

.auth-links {
  margin-left: auto;
  color: white;
}

.auth-links a {
  color: white;
  text-decoration: none;
  margin-left: 10px;
}

nav a.router-link-active {
  font-weight: bold;
}
</style>