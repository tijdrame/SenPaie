import { BaseEntity } from './../../shared';

export class Regime implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public code?: string,
        public tauxPatronal?: number,
        public tauxSalarial?: number,
        public plafond?: number,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
