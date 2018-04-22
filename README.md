# Profit Trailer to PT Feeder appsettings.json files

[![Build Status](https://travis-ci.org/mehtadone/PT2PTF.svg?branch=master)](https://travis-ci.org/mehtadone/PT2PTF)

## About

Have you found some ProfitTrailer settings online shared by a kind soul? But you use [PT Feeder](https://wiki.ptfeeder.co/) and want to add some extra intelligence onto your settings and have them change according to market and coin trend. 

Now with ProfitTrailer version 2, and its endless possiblities, this conversion becomes a lot more difficult, and PT Feeder can be complex it its own right. This is project for you. 

This is a free open source java utility that will take the three .properties files below from [ProfitTrailer Version 2](https://github.com/taniman/profit-trailer), and convert them into a [PT Feeder version 1.5 & above](https://github.com/mehtadone/PTFeeder) appsettings.json file. 

- pairs.properties
- indicators.properties
- dca.properties

If you do not have these files, you can create new ones and copy and paste them from the ProfitTrailer webpage into them. 

This will create a like for like PT Feeder appsettings file with no extra coin or market actions. 

**This then can be used as a base to build your PT Feeder strategy from.** 

## Instructions. 

- Download the zip file from the [releases](https://github.com/mehtadone/PT2PTF/releases) page. 
- Extract zip to new folder. 
- Copy the three properties files into the new folder. 
- Navigate to the folder using a command window (dos prompt, terminal, bash).
- type `java -jar pt2ptf.jar`.
- If successful, there will a new file in the directory called `appsettings.json`.
- Copy this file into the PT Feeder config directory
- Edit this file and change any properties you would like and add any extra configuration you like. See [here](https://wiki.ptfeeder.co/configuration.html#section-groupings) for all the possiblities. 

As a editor for the json file, [Visual Studio Code](https://code.visualstudio.com) is highly reccomended. 
