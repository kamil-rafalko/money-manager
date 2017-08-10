angular.
module('transaction').
component('transactionInsert', {
    template: require('./transaction-insert.template.html'),
    controller: ['Transaction', '$rootScope',
        function TransactionInsertController(Transaction, $rootScope) {
            var self = this;
            this.insertTransaction = function() {
                var date = self.date;
                var amount = self.amount;
                var category = self.category;

                if (date && amount && category) {
                    var requestObject = {
                        date: date,
                        amount: amount,
                        categoryName: category
                    };
                    var transaction = new Transaction(requestObject);
                    transaction.$save(function() {
                        $rootScope.$broadcast('transactionInsert');
                    });

                    clearData();
                }
            };

            var clearData = function() {
                self.date = '';
                self.amount = '';
                self.category = '';
            }
        }
    ]
}).
directive('validNumber', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
            if(!ngModelCtrl) {
                return;
            }

            ngModelCtrl.$parsers.push(function(val) {
                if (angular.isUndefined(val)) {
                    val = '';
                }

                var clean = val.replace(/[^-0-9.,]/g, '');
                clean = clean.replace(',', '.');
                var negativeCheck = clean.split('-');
                var decimalCheck = clean.split('.');
                if (!angular.isUndefined(negativeCheck[1])) {
                    negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
                    clean = negativeCheck[0] + '-' + negativeCheck[1];
                    if (negativeCheck[0].length > 0) {
                        clean = negativeCheck[0];
                    }
                }

                if (!angular.isUndefined(decimalCheck[1])) {
                    decimalCheck[1] = decimalCheck[1].slice(0, 2);
                    clean = decimalCheck[0] + '.' + decimalCheck[1];
                }

                if (val !== clean) {
                    ngModelCtrl.$setViewValue(clean);
                    ngModelCtrl.$render();
                }
                return clean;
            });

            element.bind('keypress', function(event) {
                if(event.keyCode === 32) {
                    event.preventDefault();
                }
            });
        }
    };
});