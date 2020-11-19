% Turun yliopiston IT-laitoksen dokumentointiohjetta noudattamaan pyrkiv� LaTeX-dokumenttiluokka.
% http://www.it.utu.fi/opiskelu/ohjaus/tutkielmienhyvaksymismenettely/index/raportointiohje_turku_-_v092005.pdf
% K�ytt�� osia Turun yliopiston fysiikan laitoksen Wihuri-tutkimuslaboratorion opinn�yte-dokumenttiluokasta.
% http://www.physics.utu.fi/laitos/wihuri/opiskelijoille.html
\ProvidesClass{itlaitos}

\LoadClass[a4paper,12pt,onecolumn,oneside,titlepage]{article}

% Ylimmän tason osioiden otsikot all-capsilla
\makeatletter
%\renewcommand{\section}{\newpage\@startsection{section}{1}{\z@ }{-3.5ex \@plus -1ex \@minus -.2ex}{2.3ex \@plus .2ex}{\normalfont \Large \bfseries \MakeUppercase}}
\renewcommand{\section}{\@startsection{section}{1}{\z@ }{-3.5ex \@plus -1ex \@minus -.2ex}{2.3ex \@plus .2ex}{\normalfont \Large \bfseries \MakeUppercase}}
\makeatother

% Sivunumerot oikeaan alakulmaan
\usepackage{fancyhdr}
\fancyhf{} % clear all header and footer fields
\rfoot{\thepage}

% Marginaalit
\usepackage[top=3cm,bottom=3cm,left=4cm,right=2cm]{geometry}

% Rivivälit
\usepackage{setspace}
\doublespacing

% Kappaleen sisennys
\setlength{\parindent}{1.2cm}


% Alla kansilehtisivun koodi, joka on lainattu suoraan 
%Seuraavassa m��ritell��n k�sky \tarkastaja{}, joka otta sy�tteeksi yhden
%tarkastajista. Jos tarkastajia on useampia, annetaan uudessa k�skyss�
%uusi tarkastaja. Jos haluat kolmanne, niin debug debug :)
\makeatletter

%K�skyn \laitos{} m��rittely.
\newif\if@haslaitos \global\@haslaitosfalse
\def\laitos#1{%
  \@for\@tempa:=#1\do{\let\@laitos\@tempa}%
  \global\@haslaitostrue%
}
%K�skyn \oppiaine{} m��rittely.
\newif\if@hasoppiaine \global\@hasoppiainefalse
\def\oppiaine#1{%
  \@for\@tempa:=#1\do{\let\@oppiaine\@tempa}%
  \global\@hasoppiainetrue%
}
%K�skyn \opinnayte{} m��rittely.
\newif\if@haslab \global\@haslabfalse
\def\opinnayte#1{%
  \@for\@tempa:=#1\do{\let\@lab\@tempa}%
  \global\@haslabtrue%
}
%K�skyn vuosi m��rittely.
\newif\if@hasvuosi \global\@hasvuosifalse
\def\vuosi#1{%
  \@for\@tempa:=#1\do{\let\@vuosi\@tempa}%
  \global\@hasvuositrue%
}

\if@titlepage
  \renewcommand\maketitle{\begin{titlepage}%
  \renewcommand{\baselinestretch}{1}\large\normalsize
  \let\footnotesize\small
  \let\footnoterule\relax
  \let \footnote \thanks
  \null\vfil
  \vskip 200\p@
  \begin{center}%
    {\LARGE \@title \par}%
%    \vskip 3em%
  \end{center}\par
  \vfill
	\if@haslab\makebox[8cm]{}\@lab\par\fi%
        \makebox[8cm]{}Turun yliopisto\par%
        \if@haslaitos\makebox[8cm]{}\@laitos\par\fi%
        \if@hasoppiaine\makebox[8cm]{}\@oppiaine\par\fi%
        \if@hasvuosi\makebox[8cm]{}\@vuosi\par\fi%
	\makebox[8cm]{}\@author\par%
	\vskip 60\p@
%    \end{tabbing}
%    {\large
%     \lineskip .75em%
%      \begin{tabular}[t]{c}%
%        \@author
%      \end{tabular}\par}%
%      \vskip 1.5em%
%    {\large \@date \par}%       % Set date in \large size.
%  \end{center}\par
%  \@thanks
%  \vfil\null
  \end{titlepage}%
  \setcounter{footnote}{0}%
  \global\let\thanks\relax
  \global\let\maketitle\relax
  \global\let\@thanks\@empty
  \global\let\@lab\@empty
  \global\let\@oppiaine\@empty
  \global\let\@vuosi\@empty
  \global\let\@author\@empty
  \global\let\@title\@empty
  \global\let\lab\relax
%  \global\let\vuosi\relax
  \global\let\title\relax
  \global\let\author\relax
  \global\let\date\relax
  \global\let\and\relax
}
\makeatother