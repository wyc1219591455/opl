import { getLanguageAll } from '@/api/cms/common';

const langList = {
    state: {
        languageList: [],
        activeLanguageList: []
    },
    actions: {
        getLanguage({ commit }) {
            return new Promise(resolve => {
                getLanguageAll().then((res) => {
                    commit('SET_LANGUAGEList', res.content);
                    resolve(res)
                })
                
            });
        }
    },
    mutations: {
        SET_LANGUAGEList: (state, data) => {
            // setToken(token)
            state.languageList = data;
        }
    }
}
export default langList;