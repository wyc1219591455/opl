<template>
    <div class="app-container">
        <basic-container>
            <form-wrap></form-wrap>
        </basic-container>
    </div>
</template>

<script>
    import formWrap from './components/form';
    export default {
        components:{
            formWrap
        },
        name: 'paper-add',
        data() {
            return {

            }
        }
}
</script>

<style lang="scss" scoped>

</style>
