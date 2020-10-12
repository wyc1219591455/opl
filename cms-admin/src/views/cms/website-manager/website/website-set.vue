<template>
    <div class="app-container">
        <basic-block title="网站配置">
            <form-wrap></form-wrap>
        </basic-block>
    </div>
</template>

<script>
    import formWrap from './components/form';
    export default {
        components:{
            formWrap
        },
        name: 'website-add',
        data() {
            return {

            }
        }
}
</script>

<style lang="scss" scoped>

</style>
