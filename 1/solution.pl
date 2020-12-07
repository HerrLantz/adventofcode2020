parser(Input ,X) :- open(Input, read, In),
             get0(In, Char),
             parse_lines(Char, X, In).

parse_lines(-1, [], _) :- !. % Break at end of stream
parse_lines(Char, [H|T], In) :-
    read_line(Char, String, In),
    convert_to_number(String, "", H),
    get0(In, Next_Char),
    parse_lines(Next_Char, T, In).


read_line(10, [], _) :- !. % Break at new line
read_line(Char, [Char | Rest], In) :-
    get0(In, Next_Char),
    read_line(Next_Char, Rest, In).


convert_to_number([], X, Y) :- number_string(Y, X), !.
convert_to_number([H|T], String, Res) :- 
    char_code(C, H),
    string_concat(String, C, New_string),
    convert_to_number(T, New_string, Res).

is_zero(X) :- X = 0.


exists_in(X, [X|_]) :- !.
exists_in(X, [_|T]) :- exists_in(X, T).


find_entries(Product) :-
    parser(input, Expence_report),
    find_entries(Expence_report, Product).
find_entries([H|T], Product) :- Y is 2020 - H,
                         exists_in(Y, T),
                         Product is (2020 - H) * H, !.
find_entries([_|T], Product) :- find_entries(T, Product).

