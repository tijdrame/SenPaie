import { BaseEntity } from './../../shared';

export class PrimeCollab implements BaseEntity {
    constructor(
        public id?: number,
        public montant?: number,
        public deleted?: boolean,
        public collaborateur?: BaseEntity,
        public prime?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
