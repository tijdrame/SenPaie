import { BaseEntity } from './../../shared';

export class Bareme implements BaseEntity {
    constructor(
        public id?: number,
        public revenuBrut?: number,
        public trimF?: number,
        public unePart?: number,
        public unePartEtDemi?: number,
        public deuxParts?: number,
        public deuxPartsEtDemi?: number,
        public troisParts?: number,
        public troisPartsEtDemi?: number,
        public quatreParts?: number,
        public quatrePartsEtDemi?: number,
        public cinqParts?: number,
        public deleted?: boolean,
    ) {
        this.deleted = false;
    }
}
