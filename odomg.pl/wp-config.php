<?php
/**
 * Podstawowa konfiguracja WordPressa.
 *
 * Ten plik zawiera konfiguracje: ustawień MySQL-a, prefiksu tabel
 * w bazie danych, tajnych kluczy, używanej lokalizacji WordPressa
 * i ABSPATH. Więćej informacji znajduje się na stronie
 * {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Kodeksu. Ustawienia MySQL-a możesz zdobyć
 * od administratora Twojego serwera.
 *
 * Ten plik jest używany przez skrypt automatycznie tworzący plik
 * wp-config.php podczas instalacji. Nie musisz korzystać z tego
 * skryptu, możesz po prostu skopiować ten plik, nazwać go
 * "wp-config.php" i wprowadzić do niego odpowiednie wartości.
 *
 * @package WordPress
 */
// ** Ustawienia MySQL-a - możesz uzyskać je od administratora Twojego serwera ** //
/** Nazwa bazy danych, której używać ma WordPress */
define('DB_NAME', 'tb152026_odo');

/** Nazwa użytkownika bazy danych MySQL */
define('DB_USER', 'tb152026_odo');

/** Hasło użytkownika bazy danych MySQL */
define('DB_PASSWORD', 'hshse645');

/** Nazwa hosta serwera MySQL */
define('DB_HOST', 'localhost');

/** Kodowanie bazy danych używane do stworzenia tabel w bazie danych. */
define('DB_CHARSET', 'utf8');

/** Typ porównań w bazie danych. Nie zmieniaj tego ustawienia, jeśli masz jakieś wątpliwości. */
define('DB_COLLATE', '');

/**#@+
 * Unikatowe klucze uwierzytelniania i sole.
 *
 * Zmień każdy klucz tak, aby był inną, unikatową frazą!
 * Możesz wygenerować klucze przy pomocy {@link https://api.wordpress.org/secret-key/1.1/salt/ serwisu generującego tajne klucze witryny WordPress.org}
 * Klucze te mogą zostać zmienione w dowolnej chwili, aby uczynić nieważnymi wszelkie istniejące ciasteczka. Uczynienie tego zmusi wszystkich użytkowników do ponownego zalogowania się.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         '_w`leS-_+Okr=qJuT[rrb-gE6G%,th%~IP1+TK1uOaP9i DJ4NrSk29d/VKLve{x');
define('SECURE_AUTH_KEY',  'B0Qz|+S3%xP$p3Q-mi38.Uw[@qO7&pHi}_o>ugEQm`{eQgl|,pQ)VRP-NaM~2ATp');
define('LOGGED_IN_KEY',    ',Cxo!rct+cu;sp]u43JJ^$3<f9Q95]S]]yV!g)-0vogSTB9hp]S8DXPk`{}<aT=3');
define('NONCE_KEY',        'aEP(({uE>8x8>=8{%$i=@4v Qo0.$q_^VpQePh!t;E^z@dj:V5x!`Kl.r%Iv NEJ');
define('AUTH_SALT',        'z]0d9=@hMh#Y>>MAad nY(}w|e[g):/B(0r|{&R5RGf$yJahL9]!q6E`AeG3$([E');
define('SECURE_AUTH_SALT', '6CllWgy @V&7?wy*2_NJDu.>(VBX/yW9p}Dc,v8I4CIW/0HMEaki9fn]k@d:<m$S');
define('LOGGED_IN_SALT',   '.; ;p/5I.@Sv-sFj7XG.ZzYdtt8m-|{Fh.l`1`ly@a-EDp1olk$fgP|S?(4$?#@D');
define('NONCE_SALT',       'XWx2cd^5-95u3>hO}&QJuj~eLrQaK`PSG8U;-@V_/Jo*RGc*+s-6O^A_3V#Dv|}O');

/**#@-*/

/**
 * Prefiks tabel WordPressa w bazie danych.
 *
 * Możesz posiadać kilka instalacji WordPressa w jednej bazie danych,
 * jeżeli nadasz każdej z nich unikalny prefiks.
 * Tylko cyfry, litery i znaki podkreślenia, proszę!
 */
$table_prefix  = 'wp_2';

/**
 * Kod lokalizacji WordPressa, domyślnie: angielska.
 *
 * Zmień to ustawienie, aby włączyć tłumaczenie WordPressa.
 * Odpowiedni plik MO z tłumaczeniem na wybrany język musi
 * zostać zainstalowany do katalogu wp-content/languages.
 * Na przykład: zainstaluj plik de_DE.mo do katalogu
 * wp-content/languages i ustaw WPLANG na 'de_DE', aby aktywować
 * obsługę języka niemieckiego.
 */
define('WPLANG', 'pl_PL');

/**
 * Dla programistów: tryb debugowania WordPressa.
 *
 * Zmień wartość tej stałej na true, aby włączyć wyświetlanie ostrzeżeń
 * podczas modyfikowania kodu WordPressa.
 * Wielce zalecane jest, aby twórcy wtyczek oraz motywów używali
 * WP_DEBUG w miejscach pracy nad nimi.
 */
define('WP_DEBUG', false);

/* To wszystko, zakończ edycję w tym miejscu! Miłego blogowania! */

/** Absolutna ścieżka do katalogu WordPressa. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Ustawia zmienne WordPressa i dołączane pliki. */
require_once(ABSPATH . 'wp-settings.php');
?>