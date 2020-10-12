<script>
export default {
    name: "MenuItem",
    functional: true,
    props: {
        icon: {
            type: String,
            default: "",
        },
        title: {
            type: String,
            default: "",
        },
    },
    render(h, context) {
        const { icon, title } = context.props;
        const vnodes = [];

        if (icon) {
            // vnodes.push(<svg-icon icon-class={icon} />);
            let classValue = `iconfont ${icon}`;
            vnodes.push(<i class={classValue}/>);    
        }

        if (title) {
            vnodes.push(<span slot="title">{title}</span>);
        }
        return vnodes;
    },
};
</script>
<style lang="scss" scoped>
.svg-icon {
    color: #bfbfbf;
}

</style>
