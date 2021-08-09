import userApi from "@/api/modules/user";
import { getToken, setToken, removeToken } from "@/utils";
//import router, { resetRouter } from "@/router";
const state = {
  token: getToken(),
  name: "",
  roles: [],
  roomlist:[],
  status:"home"
};
const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
  SETROOM_LIST: (state, roomlist) => {
    state.roomlist = roomlist;
  },
  setstatus: (state, status) => {
    state.status = status;
  },
  
};
const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      userApi
        .login({ username: username.trim(), password: password })
        .then((response) => {
          const { data } = response;
          commit("SET_TOKEN", data.token);
          setToken(data.token);
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      userApi
        .getUserInfo(state.token)
        .then((response) => {
          const { data } = response;
          if (!data) {
            reject("验证失败，请重新登录");
          }
          const { roles, name } = data;
          if (!roles || roles.length <= 0) {
            reject("getInfo: 角色必须是非空数组！");
          }

          commit("SET_ROLES", roles);
          commit("SET_NAME", name);
          resolve(data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      userApi
        .logout(state.token)
        .then(() => {
          commit("SET_TOKEN", "");
          commit("SET_ROLES", []);
          removeToken();
          // dispatch("/", null, { root: true });
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  // remove token
  resetToken({ commit }) {
    return new Promise((resolve) => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      removeToken();
      resolve();
    });
  },
  // roomlists({ commit, state }, res) {
  //   commit('SETROOM_LIST', res)
  // },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
