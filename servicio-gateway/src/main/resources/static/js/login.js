angular.module('login', [])
	.config(function($httpProvider){
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
				window.location = "/";
			}, function(response){
				self.error =  true;
			});
		};
	}]);