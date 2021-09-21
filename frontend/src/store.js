import { createStore } from 'vuex'

export const store = createStore({

  // state is where we store reactive variables
  // this.$store.state.nameOfVariable
  state() {
    return {
      user: null,
      failedLogIn: false,

    }
  },
  // we cannot update state directly, so we use mutation methods to do that
  // this.$store.commit('nameOfMutation', data)
  mutations: {
    setUser(state, user) {
      state.user = user
      state.failedLogIn = false
    },
    setFailedLogin(state, value) {
      state.failedLogIn = value
    },
  },

  //async methods that will trigger a mutation
  // this.$store.dispatch('nameOfAction')
  actions: {

    async registerUser(store, user) {
      let res = await fetch('/api/registerUser', {
        method: 'POST',
        body: JSON.stringify(user),
      })
      let loggedInUser = await res.json()
      if (loggedInUser === null) {
        console.log("Failed to register account email not valid");
        this.state.failedLogIn = true
        return
      }
      store.commit('setUser', loggedInUser)
    },

    async loginUser(store, details) {
      let res = await fetch('/api/login', {
        method: 'POST',
        body: JSON.stringify(details)
      })
      let loggedInUser = await res.json()
      if ('Error' in loggedInUser) {
        console.log("Login failed");
        this.state.failedLogIn = true;
        return
      }
      console.log("Login active");
      store.commit('setUser', loggedInUser)
    },
    async whoAmI(store) {
      let res = await fetch('/api/whoAmI')
      let user = await res.json()
      store.commit('setUser', user)
    },

    async logOff(store) {
      let res = await fetch('/api/logOff')
      store.commit('setUser', null)
      let userResponse = await res.json()
      console.log(userResponse)
    },

    async addListing(listing) {
      let res = await fetch('/api/addListing', {
        method: 'POST',
        body: JSON.stringify(listing),
      })
      console.log(222);
    }
  }
})


