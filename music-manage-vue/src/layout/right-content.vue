<template>
    <div class="app">
      <div class="content-title-box" v-text="$route.name"></div>
        <div class="content-container-box">
             <router-view></router-view>
        </div>
      
    </div>
</template>

<script>  
    export default{
      mounted(){
        console.log(this.$route);
      }
    }
</script>
<style>
  .content-container-box{
   padding: 10px; 
  }
  .content-title-box{
    /* line-height: 46px; */
    padding: 10px;
    border-bottom: solid 1px #e6e6e6;
  }
</style>