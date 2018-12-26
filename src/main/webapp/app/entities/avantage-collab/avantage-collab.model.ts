import { BaseEntity } from './../../shared';

export class AvantageCollab implements BaseEntity {
    constructor(
        public id?: number,
        public montant?: number,
        public deleted?: boolean,
        public collaborateur?: BaseEntity,
        public avantage?: BaseEntity,
    ) {
        this.deleted = false;
    }
}
