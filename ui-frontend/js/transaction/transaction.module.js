require('angular-moment-picker');
require('angular-moment-picker/dist/angular-moment-picker.css');
require('./../core/transaction/transaction.module');

angular.module('transaction', [
    'moment-picker',
    'core.transaction'
]);

require('./transaction-insert.component');
require('./transaction-list.component');