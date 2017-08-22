<?php

//        $shop = R::dispense('shop');
//        $shop->name = 'Antiques';
//        $id = R::store($shop);
//        $shop = R::load('shop',$id);    
//        $uczestnicy = R::dispense("uczestnicy");
//        $uczestnicy->nrkolejny = $id;
//        $kolejnyuczestnik = R::store($uczestnicy);
//        $_wynik =  R::getAll( 'select * from leaflet' );
//        $iterator = new ArrayIterator($_wynik);
//        var_dump(iterator_to_array($iterator, true));
//        echo sizeof($_wynik);
//        $shop = R::findLast('leaflet');
//        //$shop = R::load('leaflet',  sizeof($_wynik));  
//        echo $shop->sessionstart;
//        $shop = $prod = R::findOne('leaflet',"sessionstart = '1970 01 01 01:00:00'");
//        echo "numer indexu:";
//        echo $shop->id;
        //R::trash($shop);
//        require_once $_SERVER['DOCUMENT_ROOT'].'/resources/swiftmailer/swift_required.php';
//       
//        $transport = Swift_SmtpTransport::newInstance('smtp.wp.pl', 465);
//        $transport->setEncryption('ssl');
//        $transport->setUsername('teleputa@wp.pl');
//        $transport->setPassword('Teleputa');
//        // Create the Mailer using your created Transport
//        $mailer = Swift_Mailer::newInstance($transport);
//        // Create a message
//        $message = Swift_Message::newInstance('Wonderful Subject')
//        ->setFrom(array('teleputa@wp.pl' => 'Biuro Rachunkowe Taxman'))
//        ->setTo(array('brzaskun@o2.pl' => 'A name'))
//        ->setBody('Here is the message itself')
//        ;
//        // Send the message
//        $result = $mailer->send($message);
//        echo $result;
//          $value = 'something from somewhere';
//          //setcookie("TestCookie", $value);    
//          // Print an individual cookie
//          if($_COOKIE["TestCookie"]) {
//              echo "Masz dostep";
//          } else {
//              die('Direct access not permitted');
//          };
//        require_once('/required/LogowanieClass.php');
//        $text = new LogowanieClass();
//        $text->init("Grzegorz");
//        $sayHello = $text->sayHello();
//        echo $sayHello;
//        var_dump($text);

        //print_r($_COOKIE);
        //echo $_COOKIE["TestCookie"];
error_reporting(2);
    date_default_timezone_set('Europe/Warsaw');
    $data = date("Y-m-d H:i:s");
    echo $data;
    $datadozapisu =  date("d.m.Y");
    
?>
