parser(Input, Passwords) :-
    open(Input, read, In),
    get0(In, Char),
    parse_lines(In, Char, Passwords).

parse_lines(_, -1, []) :- !.
parse_lines(In, Char1, [[Min,Max,C|Password]|T]) :-
    read_until(In, Char1, 45, Num1), % Read until dash
    number_string(Min, Num1),
    get0(In, Char2),
    read_until(In, Char2, 32, Num2), % Read until space
    number_string(Max, Num2),
    get0(In, Char3),
    read_until(In, Char3, 58, [Char_code]), % Read until colon
    char_code(C, Char_code),
    get0(In, _),
    get0(In, Char4),
    read_until(In, Char4, 10, Char_codes), % Read until new line
    stringify(Char_codes, Password),
    get0(In, Char5),
    parse_lines(In, Char5, T).

read_until(_, Until, Until, []) :- !.
read_until(_, -1, _, []) :- !.
read_until(In, Char, Until, [Char|T]) :-
    get0(In, New_Char),
    read_until(In, New_Char, Until, T).

stringify([], []) :- !.
stringify([H|T], [Char|Rest]) :-
    char_code(Char, H),
    stringify(T, Rest).

count_letters(Letter, L, Res) :- count_letters(Letter, L, 0, Res).
count_letters(_, [], Sum, Sum) :- !.
count_letters(Letter, [Letter|T], Sum, Res) :-
    New_sum is Sum + 1,
    count_letters(Letter, T, New_sum, Res), !.
count_letters(Letter, [_|T], Sum, Res) :- count_letters(Letter, T, Sum, Res).

count_valid_passwords(Res) :-
    parser(input, Passwords),
    count_valid_passwords(Passwords, 0, Res).
count_valid_passwords([], Sum, Sum) :- !.
count_valid_passwords([[Low,High,Letter|Password]|Rest], Sum, Res) :-
    count_letters(Letter, Password, N),
    N >= Low,
    N =< High,
    New_sum is Sum + 1,
    count_valid_passwords(Rest, New_sum, Res), !.
count_valid_passwords([_|T], Sum, Res) :- count_valid_passwords(T, Sum, Res).

