<?php

class Mail {

    public static function mailautomat($imienazwisko, $plec, $email, $szkolenieuser, $id_uzytkownik) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        $poziomzaswiadczenie = Mail::pobierzPoziomZaswiadczenia($szkolenieuser);
        $instrukcja = R::getCell("SELECT instrukcja FROM szkoleniewykaz WHERE  nazwa = '$szkolenieuser'");
        $linia1 = Mail::pobierzLinia1Zaswiadczenia($szkolenieuser);
        try {
            // Create the Mailer using your created Transport 
            $mailer = Mail::mailerFactory();
            $logger = Mail::loggerFactory($mailer);
            // Create a message
            $message = null;
            if ($plec === "k") {
                $message = Swift_Message::newInstance('Rejestracja do e-szkolenia ' . $linia1) 
                        ->setContentType('text/plain')
                        ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setTo(array($email => $imienazwisko))
                        ->setBody('
         <h4>Dzień dobry,</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Pani adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/'.$instrukcja.'">instrukcja do e-szkolenia,</a></p>
         Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
		 <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
          
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ', 'text/html');
            } else {
                $message = Swift_Message::newInstance('Rejestracja do e-szkolenia ' . $linia1)
                        ->setContentType('text/plain')
                        ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setTo(array($email => $imienazwisko))
                        ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setBody('
        <h4>Dzień dobry,</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Pana adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/'.$instrukcja.'">instrukcja do e-szkolenia,</a></p>
         Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
		 <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ', 'text/html');
            }
            // Send the message
            $failedRecipients = array();
            $numSent = 0;
            // Send the message
            $numSent = $mailer->send($message, $failedRecipients);
            if ($numSent == 0) {
                Mail::mailniewyslano($failedRecipients[0],$logger);
                throw new Exception("Wystąpił błąd. Nie wysłano maila z linkiem i instrukcja do użytkownika $email");
            } else if ($numSent == 1) {
                $sql = "UPDATE  `uczestnicy` SET  `wyslanymailupr` = '1' WHERE  `uczestnicy`.`id` = $id_uzytkownik;";
                $res = R::exec($sql);
            }
        } catch (Exception $error) {
            return $email;
            //Mail::mailerror($error); 
        }
    }
    
    public final static function pobierzPoziomZaswiadczenia($szkolenie, $plec) { 
        $czy_jest_zaswiadczenie = (int)R::getCell("SELECT id_zaswiadczenie FROM szkoleniewykaz WHERE nazwa = '$szkolenie'");
        $poziomzaswiadczenie = "";
        if ($czy_jest_zaswiadczenie) {
            $poziomzaswiadczenie = R::getCell("SELECT poziom FROM zaswiadczenia WHERE id = '$czy_jest_zaswiadczenie'");
        }
        switch ($szkolenie) {
            case 'szkolenie1' : 
                $poziomzaswiadczenie = "poziom BASIC";
                break;
            case 'szkolenie2' :
                $poziomzaswiadczenie = "poziom OPTIMUM";
                break;
            case 'szkolenie3' :
                $poziomzaswiadczenie = "poziom PREMIUM";
                break;
        }
        return $poziomzaswiadczenie;
    }
    
     public final static function pobierzLinia1Zaswiadczenia($szkolenie) { 
        $czy_jest_zaswiadczenie = (int)R::getCell("SELECT id_zaswiadczenie FROM szkoleniewykaz WHERE nazwa = '$szkolenie'");
        $linia1 = "";
        if ($czy_jest_zaswiadczenie) {
            $linia1 = R::getCell("SELECT linia1 FROM zaswiadczenia WHERE id = '$czy_jest_zaswiadczenie'");
        }
        return $linia1;
    }

    public static function mailcertyfikat($imienazwisko, $plec, $email, $filename, $poziomzaswiadczenie, $kontakt, $bcc, $szkolenieuser, $id) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $linia1 = Mail::pobierzLinia1Zaswiadczenia($szkolenieuser);
            try {
                // Create the Mailer using your created Transport
                $mailer = Mail::mailerFactory();
                $logger = Mail::loggerFactory($mailer);
                // Create a message
                $message = null;
                if ($plec === "k") {
                    $message = Swift_Message::newInstance('Zaświadczenie ukończenia e-szkolenia - '. $linia1)
                            ->setContentType('text/plain')
                            ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setTo(array($email => $imienazwisko))
                            ->setBcc(array($bcc => $kontakt));
                    $message->setBody('
                    <!DOCTYPE html><html lang="pl">
                    <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
                    <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
                    <div style="text-align: left; font-size: 12pt; height: 250px; color: rgb(74,26,15);">
                    <p>Dzień dobry.</p>
                    <p>Gratulujemy ukończenia szkolenia ' . $poziomzaswiadczenie . '</p>
                    <p>przygotowanego przez ODO Management Group.</p>
                    <p>W załączniku tej wiadomości znajduje się zaświadczenie (plik PDF) potwierdzające zaliczenie testu.</p>
                    <br/>
                    <p>Dziękujemy za skorzystanie z naszego systemu e-szkoleń.</p>
                    <p>Zespół ODO Management Group</p><br/>
                    <img src="' . // Embed the file
                                    $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector.png')) .
                                    '" width="93" height="61"><span style="margin-left: 5px; color: white;">a</span>
                    <img src="' . // Embed the file
                                    $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector1.png')) .
                                    '" width="152" height="32" margin-left="5">
                    <p style="font-weight: bold; font-size: smaller;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
                    </div>
                    <div style="height: 40px;">
                    </div>
                    </body></html> 
                ', 'text/html');
                } else {

                    $message = Swift_Message::newInstance('Zaświadczenie ukończenia e-szkolenia - '. $linia1)
                            ->setContentType('text/plain')
                            ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setTo(array($email => $imienazwisko))
                            ->setBcc(array($bcc => $kontakt))
                            ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'));
                    $message->setBody('
                    <!DOCTYPE html><html lang="pl">
                    <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
                    <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
                    <div style="text-align: left; font-size: 12pt; height: 250px; color: rgb(74,26,15);">
                    <p>Dzień dobry.</p>
                     <p>Gratulujemy ukończenia szkolenia ' . $poziomzaswiadczenie . '</p>
                    <p>przygotowanego przez ODO Management Group.</p>
                    <p>W załączniku tej wiadomości znajduje się zaświadczenie (plik PDF) potwierdzające zaliczenie testu.</p>
                    <br/>
                    <p>Dziękujemy za skorzystanie z naszego systemu e-szkoleń.</p>
                    <p>Zespół ODO Management Group</p><br/>
                    <img src="' . // Embed the file
                                    $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector.png')) .
                                    '" width="93" height="61"><span style="margin-left: 5px; color: white;">a</span>
                    <img src="' . // Embed the file
                                    $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector1.png')) .
                                    '" width="152" height="32">
                    <p style="font-weight: bold; font-size: smaller;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
                    </div>
                    <div style="height: 40px;">
                    </div>
                    </body></html>
                ', 'text/html');
                }
                //zalacz plik
                $message->attach(Swift_Attachment::fromPath($filename));
                // Send the message
                $failedRecipients = array();
                $numSent = 0;
                // Send the message
                $numSent = $mailer->send($message, $failedRecipients);
                if ($numSent == 0) {
                    throw new Exception("Wystąpił błąd. Nie wysłano certyfikatu ukończenia do użytkownika $email");
                } else if ($numSent > 0) {
                    $niewyslano = NULL;
                    if ($numSent == 1) {
                        $niewyslano = $failedRecipients[0];
                        Mail::mailniewyslano($niewyslano,$logger);
                    }
                    $sql = "UPDATE uczestnicy SET wyslanycert = 1 WHERE id = '$id'";
                    $res = R::exec($sql);
    //                if ($res == 1) {
    //                    throw new Exception("Nie udało się zaznaczyć tego w tabeli wysłania certyfikatu ukończenia");
    //                }
                }
            } catch (Exception $e) {
                Mail::mailerror($e);
            }
        }
    }
    
    
    public static function mailupowaznienie($imienazwisko, $plec, $email, $filename, $poziomzaswiadczenie, $kontakt, $bcc, $id) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
            try {
                // Create the Mailer using your created Transport
                $mailer = Mail::mailerFactory();
                $logger = Mail::loggerFactory($mailer);
                // Create a message
                $message = null;
                if ($plec === "k") {
                    $message = Swift_Message::newInstance('Upoważnienie do przetwarzania danych osobowych')
                            ->setContentType('text/plain')
                            ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setTo(array($email => $imienazwisko))
                            ->setBcc(array($bcc => $kontakt));
                    $message->setBody('
            <!DOCTYPE html><html lang="pl">
            <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
            <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
            <div style="text-align: left; font-size: 12pt; height: 250px; color: rgb(74,26,15);">
            <p>Dzień dobry.</p>
            <p>W załączniku tej wiadomości znajduje się upoważnienie do przetwarzania danych osobowych (plik PDF), należy je niezwłocznie wydrukować oraz podpisać w miejscu oznaczonym i przekazać przełożonemu lub innej osobie zgodnie z przyjętą w twojej firmie procedurą nadawania upoważnień.</p>
            <br/>
            <p>Dziękujemy za skorzystanie z naszego systemu e-szkoleń.</p>
            <p>Zespół ODO Management Group</p><br/>
            <img src="' . // Embed the file
                            $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector.png')) .
                            '" width="93" height="61"><span style="margin-left: 5px; color: white;">a</span>
            <img src="' . // Embed the file
                            $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector1.png')) .
                            '" width="152" height="32" margin-left="5">
            <p style="font-weight: bold; font-size: smaller;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
            </div>
            <div style="height: 40px;">
            </div>
            </body></html> 
        ', 'text/html');
                } else {

                    $message = Swift_Message::newInstance('Upoważnienie do przetwarzania danych osobowych')
                            ->setContentType('text/plain')
                            ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                            ->setTo(array($email => $imienazwisko))
                            ->setBcc(array($bcc => $kontakt))
                            ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'));
                    $message->setBody('
            <!DOCTYPE html><html lang="pl">
            <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
            <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
            <div style="text-align: left; font-size: 12pt; height: 250px; color: rgb(74,26,15);">
            <p>Dzień dobry.</p>
            <p>W załączniku tej wiadomości znajduje się upoważnienie do przetwarzania danych osobowych (plik PDF), należy je niezwłocznie wydrukować oraz podpisać w miejscu oznaczonym i przekazać przełożonemu lub innej osobie zgodnie z przyjętą w twojej firmie procedurą nadawania upoważnień.</p>
            <br/>
            <p>Dziękujemy za skorzystanie z naszego systemu e-szkoleń.</p>
            <p>Zespół ODO Management Group</p><br/>
            <img src="' . // Embed the file
                            $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector.png')) .
                            '" width="93" height="61"><span style="margin-left: 5px; color: white;">a</span>
            <img src="' . // Embed the file
                            $message->embed(Swift_Image::fromPath($_SERVER['DOCUMENT_ROOT'] . '/resources/css/pics/ODOLogoVector1.png')) .
                            '" width="152" height="32">
            <p style="font-weight: bold; font-size: smaller;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
            </div>
            <div style="height: 40px;">
            </div>
            </body></html>
        ', 'text/html');
                }
                //zalacz plik
                $message->attach(Swift_Attachment::fromPath($filename));
                $failedRecipients = array();
                $numSent = 0;
                // Send the message 
                $numSent = $mailer->send($message, $failedRecipients);
                if ($numSent == 0) {
                    throw new Exception("Wystąpił błąd. Nie wysłano upoważnienia do użytkownika $email");
                } else if ($numSent > 0) {
                    $niewyslano = NULL;
                    if ($numSent == 1) {
                        $niewyslano = $failedRecipients[0];
                        Mail::mailniewyslano($niewyslano,$logger);
                    }
                    $sql = "UPDATE uczestnicy SET  wyslaneup = 1 WHERE id = '$id'";
                    $res = R::exec($sql);
    //                if ($res == 1) {
    //                    throw new Exception("Wysłano mail z upoważnieniem na adres $email, ale nie udało się zaznaczyć tego w tabeli");
    //                }
                }
            } catch (Exception $e) {
                Mail::mailerror($e);
            }
        }
    }
    
    private static function mailerFactory() {
        $transport = Swift_SmtpTransport::newInstance('az0066.srv.az.pl', 465);
        $transport->setEncryption('ssl');
        $transport->setUsername('e-szkolenia@odomg.pl');
        $transport->setPassword('Odo1234*');
        // Create the Mailer using your created Transport
        return Swift_Mailer::newInstance($transport);
    }
    
    public static function loggerFactory($mailer) {
        // To use the ArrayLogger
        //The Logger plugins helps with debugging during the process of sending.
        // It can help to identify why an SMTP server is rejecting addresses, or any other hard-to-find problems that may arise.
        $logger = new Swift_Plugins_Loggers_ArrayLogger();
        $mailer->registerPlugin(new Swift_Plugins_LoggerPlugin($logger));
        return $logger;
    }
    
    public static function mailniewyslano($niewyslano,$logger) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        try {
            // Create the Mailer using your created Transport
            $mailer = Mail::mailerFactory();
            $logger = Mail::loggerFactory($mailer);
            // Create a message 
            $message = Swift_Message::newInstance('Odo raport o błędach') 
                        ->setContentType('text/plain')
                        ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setTo(array("brzaskun@gmail.com" => "Grzegorz Grzelczyk"))
                        ->setBcc(array("mchmielewska@interia.pl" => "Magdalena Chmielewska"))
                        ->setBody('<!DOCTYPE html><html lang="pl">
                        <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
                        <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
                        <div style="text-align: left; font-size: 12pt; height: 200px; color: rgb(74,26,15);">'
                                . '<p> id: '.$_SESSION['uczestnik']['id'].'</p>'
                                . '<p> email zalogowanej osoby: '.$_SESSION['uczestnik']['email'].'</p>'
                                . '<p> szkolenie: '.$_SESSION['uczestnik']['nazwaszkolenia'].'</p>'
                                . '<p> nie wysłano maila do: : '.$niewyslano.'</p>'
                                . '</div>
                        <div>
                        <span>treść loggera</span>
                        <p>'.$logger->dump().'</p>
                        </div>
                        </body></html>
                        ', 'text/html');
            $mailer->send($message);
        } catch (Exception $e) {
            
        }
    }
    
    public static function mailerror($error) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        try {
            // Create the Mailer using your created Transport
            $mailer = Mail::mailerFactory();
            $logger = Mail::loggerFactory($mailer);
            // Create a message 
            $message = Swift_Message::newInstance('Odo raport o błędach') 
                        ->setContentType('text/plain')
                        ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setTo(array("brzaskun@gmail.com" => "Grzegorz Grzelczyk"))
                        ->setBcc(array("mchmielewska@interia.pl" => "Magdalena Chmielewska"))
                        ->setBody('<!DOCTYPE html><html lang="pl">
                        <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
                        <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
                        <div style="text-align: left; font-size: 12pt; height: 200px; color: rgb(74,26,15);">'
                                . '<p> plik: '.$error->getFile().'</p>'
                                . '<p> wiersz: '.$error->getLine().'</p>'
                                . '<p> wiadomość '.$error->getMessage().'</p>'
                                . '<p> kod '.$error->getCode().'</p>'
                                . '<p> trace: '.$error->getTraceAsString().'</p>'
                                . '</div>'
                                . '<div style="text-align: left; font-size: 12pt; height: 350px; color: rgb(74,26,15);">'
                                . '<p> id: '.$_SESSION['uczestnik']['id'].'</p>'
                                . '<p> email zalogowanej osoby: '.$_SESSION['uczestnik']['email'].'</p>'
                                . '<p> szkolenie: '.$_SESSION['uczestnik']['nazwaszkolenia'].'</p>'
                                . '</div>
                        </body></html>
                        ', 'text/html');
            $mailer->send($message);
        } catch (Exception $e) {
            
        }
    }
    
    public static function mailerror2($error, $email, $szkolenie) {
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
        try {
            // Create the Mailer using your created Transport
            $mailer = Mail::mailerFactory();
            $logger = Mail::loggerFactory($mailer);
            // Create a message 
            $message = Swift_Message::newInstance('Odo raport o błędach') 
                        ->setContentType('text/plain')
                        ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                        ->setTo(array("brzaskun@gmail.com" => "Grzegorz Grzelczyk"))
                        ->setBcc(array("mchmielewska@interia.pl" => "Magdalena Chmielewska"))
                        ->setBody('<!DOCTYPE html><html lang="pl">
                        <head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
                        <link rel="stylesheet" href="/resources/css/zaswiadczenie.css"/></head><body>
                        <div style="text-align: left; font-size: 12pt; height: 200px; color: rgb(74,26,15);">'
                                . '<p> plik: '.$error->getFile().'</p>'
                                . '<p> wiersz: '.$error->getLine().'</p>'
                                . '<p> wiadomość '.$error->getMessage().'</p>'
                                . '<p> kod '.$error->getCode().'</p>'
                                . '<p> trace: '.$error->getTraceAsString().'</p>'
                                . '</div>'
                                . '<div style="text-align: left; font-size: 12pt; height: 350px; color: rgb(74,26,15);">'
                                . '<p> id: '.$_SESSION['uczestnik']['id'].'</p>'
                                . '<p> email zalogowanej osoby: '.$email.'</p>'
                                . '<p> szkolenie: '.$szkolenie.'</p>'
                                . '</div>
                        </body></html>
                        ', 'text/html');
            $mailer->send($message);
        } catch (Exception $e) {
            
        }
    }

    
     
}

?>
