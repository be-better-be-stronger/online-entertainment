module.exports = { 
  extends: ['@commitlint/config-conventional'], 
  rules: { 
    'scope-enum': [2, 'always', [ 'auth', 'user', 'video', 'admin', 'dao', 'service' ]] 
  } 
};