import { BaseEntity, User } from './../../shared';

export class Bulletin implements BaseEntity {
    constructor(
        public id?: number,
        public retenueIpm?: number,
        public retenuePharmacie?: number,
        public autreRetenue?: number,
        public deleted?: boolean,
        public dateCreated?: any,
        public dateUpdated?: any,
        public dateDeleted?: any,
        public brutFiscal?: number,
        public netAPayer?: number,
        public salaireBrutMensuel?: number,
        public impotSurRevenu?: number,
        public trimf?: number,
        public ipresPartSalariale?: number,
        public totRetenue?: number,
        public ipresPartPatronales?: number,
        public cssAccidentDeTravail?: number,
        public cssPrestationFamiliale?: number,
        public ipmPatronale?: number,
        public contributionForfaitaire?: number,
        public nbPart?: number,
        public nbFemmes?: number,
        public nbEnfants?: number,
        public collaborateur?: BaseEntity,
        public typePaiement?: BaseEntity,
        public userCreated?: User,
        public userUpdated?: User,
        public userDeleted?: User,
        public remboursements?: BaseEntity[],
    ) {
        this.deleted = false;
        this.retenueIpm = 0;
        this.retenuePharmacie = 0;
        this.salaireBrutMensuel = 0;
    }
}
