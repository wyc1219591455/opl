<template>
  <div class="avue-top">
    <div class="top-bar__left">
      <div class="avue-breadcrumb"
           :class="[{ 'avue-breadcrumb--active': isCollapse }]"
           v-if="showCollapse">
        <i class="el-icon-s-fold" @click="setCollapse"></i>
      </div>
    </div>
    <!--一级菜单-->
    <div class="top-bar__title">
      <div class="top-bar__item top-bar__item--show"
           v-if="showMenu">
        <top-menu></top-menu>
      </div>
    </div>
    <div class="top-bar__right">
      <!--<el-tooltip v-if="showColor"-->
      <!--effect="dark"-->
      <!--:content="$t('navbar.color')"-->
      <!--placement="bottom">-->
      <!--<div class="top-bar__item">-->
      <!--<top-color></top-color>-->
      <!--</div>-->
      <!--</el-tooltip>-->
      <!--<el-tooltip v-if="showDebug"-->
      <!--effect="dark"-->
      <!--:content="logsFlag?$t('navbar.bug'):logsLen+$t('navbar.bugs')"-->
      <!--placement="bottom">-->
      <!--<div class="top-bar__item top-bar__item&#45;&#45;show">-->
      <!--<top-logs></top-logs>-->
      <!--</div>-->
      <!--</el-tooltip>-->
      <!--<el-tooltip v-if="showLock"-->
      <!--effect="dark"-->
      <!--:content="$t('navbar.lock')"-->
      <!--placement="bottom">-->
      <!--<div class="top-bar__item">-->
      <!--<top-lock></top-lock>-->
      <!--</div>-->
      <!--</el-tooltip>-->
      <!--<el-tooltip v-if="showTheme"-->
      <!--effect="dark"-->
      <!--:content="$t('navbar.theme')"-->
      <!--placement="bottom">-->
      <!--<div class="top-bar__item top-bar__item&#45;&#45;show">-->
      <!--<top-theme></top-theme>-->
      <!--</div>-->
      <!--</el-tooltip>-->
      <!--<el-tooltip effect="dark"-->
      <!--:content="$t('navbar.language')"-->
      <!--placement="bottom">-->
      <!--<div class="top-bar__item top-bar__item&#45;&#45;show">-->
      <!--<top-lang></top-lang>-->
      <!--</div>-->
      <!--</el-tooltip>-->
      <el-tooltip v-if="showFullScren"
                  effect="dark"
                  :content="isFullScren?$t('navbar.screenfullF'):$t('navbar.screenfull')"
                  placement="bottom">
        <div class="top-bar__item">
          <i :class="isFullScren?'icon-huanyuanhuabu':'icon-fangda1'"
             @click="handleScreen"></i>
        </div>
      </el-tooltip>
      <!--<div class="top-bar__item top-bar__item&#45;&#45;show">-->
      <!--<top-tip></top-tip>-->
      <!--</div>-->
      <!--<img class="top-bar__img" :src="avatarUrl ? avatarUrl : 'img/bg/profile-1.png'">-->
      <img class="top-bar__img" :src="avatarUrl">
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ userInfo && userInfo.username }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown" class="jst-user-info-drop">
          <el-dropdown-item>
            <router-link to="/wel/index">{{ $t('navbar.dashboard') }}</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <router-link tag='a' to="/x-info/index">{{ $t('navbar.userinfo') }}</router-link>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout">{{ $t('navbar.logOut') }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import {mapGetters, mapState} from "vuex";
import {fullscreenToggel, listenfullscreen} from "@/util/util";
import topLock from "./top-lock";
import topMenu from "./top-menu";
import topTip from "./top-tip";
import {baseUrl} from '@/config/env';
// import topSearch from "./top-search";
import topTheme from "./top-theme";
import topLogs from "./top-logs";
import topColor from "./top-color";
import topLang from "./top-lang";
import { apiLogout } from '@/api/user'

export default {
  components: {
    topLock,
    topMenu,
    // topSearch,
    topTheme,
    topLogs,
    topColor,
    topLang,
    topTip,
  },
  name: "top",
  data() {
    return {
      // avatarUrl: '',
    };
  },
  filters: {},
  created() {
    // var str = this.userInfo.avatarPath;
    // this.avatarUrl = baseUrl + '/avatar' + str.split("avatar")[1];
  },
  mounted() {
    listenfullscreen(this.setScreen);
  },
  computed: {
    ...mapState({
      showDebug: state => state.common.showDebug,
      showTheme: state => state.common.showTheme,
      showLock: state => state.common.showLock,
      showFullScren: state => state.common.showFullScren,
      showCollapse: state => state.common.showCollapse,
      showSearch: state => state.common.showSearch,
      showMenu: state => state.common.showMenu,
      showColor: state => state.common.showColor
    }),
    ...mapGetters([
      "userInfo",
      "isFullScren",
      "tagWel",
      "tagList",
      "isCollapse",
      "tag",
      "logsLen",
      "logsFlag"
    ]),
    avatarUrl() {
      var str = this.userInfo.avatarPath;
      let url = this.userInfo.avatarPath ? baseUrl + 'avatar' + str.split("avatar")[1] : 'img/bg/profile-1.png';
      return url;
    }
  },
  methods: {
    handleScreen() {
      fullscreenToggel();
    },
    setCollapse() {
      this.$store.commit("SET_COLLAPSE");
    },
    setScreen() {
      this.$store.commit("SET_FULLSCREN");
    },
    logout() {
      console.log('12121221')
      apiLogout().then((res) => {
		this.$confirm(this.$t("logoutTip"), this.$t("tip"), {
			confirmButtonText: this.$t("submitText"),
			cancelButtonText: this.$t("cancelText"),
			type: "warning"
		}).then(() => {
			this.$store.dispatch("LogOut").then(() => {
				//this.$router.push({ path: "/login" });
				// console.log("reload")
				location.reload() // 为了重新实例化vue-router对象 避免bug
			});
		});
      }).catch((error) => {
		console.log('已取消');
		console.log(error);
      });
      
    }
  }
};
</script>

<style lang="scss">
.jst-user-info-drop.el-dropdown-menu .el-dropdown-menu__item:focus, .jst-user-info-drop.el-dropdown-menu .el-dropdown-menu__item:not(.is-disabled):hover {
  a {
    color: #fff;
  }
}

.jst-user-info-drop.el-dropdown-menu .el-dropdown-menu__item {
  border-bottom: 1px solid #e7e7e7;

  &:last-child {
    border-bottom: none;
  }

}

.avue-breadcrumb:hover {
  cursor: pointer;
}


</style>
