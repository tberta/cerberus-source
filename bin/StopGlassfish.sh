#!/bin/bash

#########################################################
#          Cerberus Application Stop                    #
#########################################################

. `dirname $0`/00Config.sh

###### Script start here ######

cd $MYPATH

### Starting instance.
$GLASSFISHPATH/asadmin stop-domain

cd -
