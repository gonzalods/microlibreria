angular.module('login', ['ngRoute','ngCookies','csrf-cross-domain'])
	.config(function($httpProvider, csrfCDProvider){
		csrfCDProvider.setAllowedMethods(['POST']);
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	})
	.controller('LoginCtrl', ['$http', function($http){
		
		var self = this;
		
		self.credenciales = {};
		
		self.login = function(){
			
			var headers = {
					authorization : "Basic " + btoa(self.credenciales.nombreusuario + ":" + 
							self.credenciales.password)
			};
			
			$http.get('/user', {headers : headers})
			.then(function(response){
				var data = response.data;
				var roles = data.roles;
				if(roles.indexOf("ROLE_ADMIN")>-1){
					window.location = "/admin";
				}else{
					window.location = "/";
				}
			}, function(response){
				self.error =  true;
			});
		};
	}])
	.controller('RegistroCtrl',['$http', function($http){
		
		var self = this;
		
		self.credenciales = {};
		console.log('Entra en controlador   ');
		
		self.nuevoregistro = function(){
			console.log('Se empieza a registrar');
			$http.post('/registrarse', self.credenciales)
			.then(function(response){
				var headers = {
						authorization : "Basic " + btoa(self.credenciales.nombreusuario + ":" + 
								self.credenciales.password)
				};
				$http.get('/user', {headers : headers})
				.then(function(response){
					$http.get('/token').then(function(response){
						$http({
							url : '/cliente',
							method : 'POST',
							headers : {
								'X-Auth-Token' : response.data.token
							},
							data: {nombreusuario: self.credenciales.nombreusuario}
						}).then(function(response){
							document.getElementById('formcuentanueva').submit();
						}, function(response){
							self.error =  true;
							self.errmsg = 'La cuenta no se ha creado correctamente';
						});
					});
				}, function(response){
					self.error =  true;
					self.errmsg = 'La cuenta no se ha creado correctamente';
				});
			}, function(response){
				self.error =  true;
				self.errmsg = response.data;
			});
		};
	}]);