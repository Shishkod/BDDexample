Scenario: positive
Given User is testing CheckText
And List of languages is en
And User wants to find repetitions
When User checks text 'support'
Then There are 0 mistakes

Scenario: basic negative
Given User is testing CheckText
And List of languages is en
When User checks text 'to to to suport collaborations betwen artists, scientists, engineirs and researzhers to develop more creative'
Then There are 4 mistakes
And Word 'betwen' is unknown
And For word 'betwen' CheckText proposes next fix 'between'

Scenario: several parameters
Given User is testing CheckText
And List of languages is en
And User wants to find repetitions
When User checks text 'to to to suport collaborations betwen artists, scientists, engineirs and researzhers to develop more creative'
Then There are 6 mistakes
And Word 'to' is repeated more than 1 time

Scenario: parametrized test
Given User is testing CheckText
And List of languages is <language>
When User checks text '<text>'
Then Word '<mistake>' is unknown
And For word '<mistake>' CheckText proposes next fix '<fix>'

Examples:
|text|language|mistake|fix|
|to suport|en|suport|support|
|коппия|ru|коппия|копия|
|мовоою|uk|мовоою|мовою|

Scenario: 3 languages
Given User is testing CheckText
And List of languages is en, ru, uk
And User wants to find repetitions
When User checks text 'suport коппия мовоою'
Then There are 3 mistakes
And For word 'suport' CheckText proposes next fix 'support'
And For word 'коппия' CheckText proposes next fix 'копия'
And For word 'мовоою' CheckText proposes next fix 'мовою'
!--As you can see assertion for Ukranian word fails but it works if you send it separately. I think it's a bug

Scenario: 3 languages with array
Given User is testing CheckText
And List of languages is en, ru, uk
And User wants to find repetitions
When User checks text 'suport коппия мовоою'
Then Next words have mistakes suport, коппия, мовоою




