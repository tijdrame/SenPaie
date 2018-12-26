import { BaseEntity, User } from './../../shared';

export class Collaborateur implements BaseEntity {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public matricule?: string,
        public adresse?: string,
        public tauxHoraire?: number,
        public salaireDeBase?: number,
        public surSalaire?: number,
        public retenueRepas?: number,
        public deleted?: boolean,
        public dateNaissance?: any,
        public photoContentType?: string,
        public photo?: any,
        public login?: string,
        public email?: string,
        public primeTransport?: number,
        public telephone?: string,
        public numeroRib?: string,
        public fonction?: BaseEntity,
        public categorie?: BaseEntity,
        public nationalite?: BaseEntity,
        public statut?: BaseEntity,
        public situationMatrimoniale?: BaseEntity,
        public typeContrat?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
        public regime?: BaseEntity,
        public userCollab?: User,
        public sexe?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
