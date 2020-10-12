<template>
    <div>
        <isp-home></isp-home>
    </div>
</template>

<script>
    import ispHome from "./../isp/isp-home";
    export default {
        name: "index",
        components: {
            ispHome,
        },
    }
</script>

<style scoped>
    .home-table-title{
        padding: 0 20px 20px 20px;
        border-bottom: 1px solid #e7e7e7;
        margin: 0 -20px;
    }
    .home-table-title+.home-tables{
        margin-top: 20px;
    }
</style>
