var demoApp = angular.module('todo', []);
	demoApp.controller('MainController', [ '$scope', 'todoWebService', function($scope, todoWebService) {
		// Setup a view model
		var vm = {};
		
		vm.list = [];
		
		// Start the initial load of lists
		todoWebService.getItems().then(function (response) {
			vm.list = response.data;
		});
		
		vm.addItem = function () {
			var item = {
				details: vm.newItemDetails
			};
			
			// Clear it from the UI
			vm.newItemDetails = '';
			
			// Send the request to the server and add the item once done
			todoWebService.addItem(item)
			.then(function (response) {
				vm.list.push({
				id: response.data.id,
				details: response.data.details
				});
			}).catch(function (error) {
				console.log(error);
			});
		};
		
		vm.removeItem = function (itemToRemove) {
			// Remove it from the list and send the server request
			vm.list = vm.list.filter(function (item) { 
				return item.id !== itemToRemove.id; 
			});
			
			todoWebService.removeItem(itemToRemove);
		};
			
		// For new items:
		vm.newItemDetails = '';
		
		// expose the vm using the $scope
		$scope.vm = vm;
	}]);
		
	demoApp.service('todoWebService', ['$http', function ($http) {
		var root = '/todo/rest/items';
		return {
			getItems: function () {
				return $http.get(root);
			},
			addItem: function (item) {
				return $http.post(root, item);
			},
			removeItem: function (item) {
				return $http.delete(root + '/' + item.id);
			}
		}
	}]);