export default [
  // app
  {
    path: '/',
    component: '../Main',
    routes: [
      // recipe
      { path: '/', redirect: '/recipe/list'},
      {
        path: '/recipe',
        name: 'recipe',
        icon: 'recipe',
        component: './Main'
      }
    ]
  }
];
