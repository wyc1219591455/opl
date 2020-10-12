import BigPicture from 'bigpicture';
import { Message } from 'element-ui';

export default (el, imgSrc) => BigPicture({
    el: el.currentTarget,
    imgSrc,
    onError() {
        Message.info({
            message: '资源加载失败，请点击重试',
            duration: 1500
        });
    }
});
