INSERT INTO `user` (`code_utilisateur`, `adresse`,`code_postal`,`mail`,`mot_de_passe`,`nom`,`id_roles`) VALUES
(1,  '3 rue du cerisier','45121', 'bob@gmail.com', 'bob','bob',1);


INSERT INTO `roles` (`code_role`, `nom`) VALUES
(1, 'user');


INSERT INTO `competence` (`code_competence`, `description`,`type`,`nom`,`id_user`) VALUES
(1, 'gateau chocolat', 'cuisine', 'gateau',1),
(2, 'changer un pneu', 'mecanique', 'pneu',1);
