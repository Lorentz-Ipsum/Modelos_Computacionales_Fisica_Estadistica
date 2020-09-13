
# Para que la bibliografía quede bien:

Primero, correr el comando en pandoc[^pandoc-bibtohtml]:

```
pandoc main_improve.tex -o test.html --bibliography bib.bib
```

La regex que encuentra los enlaces será:

```
\<a href\=\"Bibliografia\.html\#(.+?)\"\>(.+?)\<\/a\>
```

Para que en Bibliografia.html se destaque el elemento al que has clicado, hace falta la CSS [^id-highlight]:

```
 :target {
  background-color: #ffa;
}
```

[^pandoc-bibtohtml]: https://tex.stackexchange.com/questions/171793/bibtex-to-html-markdown-etc-using-pandoc

[^id-highlight]: https://stackoverflow.com/questions/11142125/css-highlight-a-div-when-the-id-is-linked-to-using-an-anchor
