import { BaseEntity } from './../../shared';

export const enum Statut {
    'GO',
    'NOGO',
    'STANDBY',
    'INPROGRESS'
}

export class Placement implements BaseEntity {
    constructor(
        public id?: number,
        public nomClientFinal?: string,
        public nomSSII?: string,
        public contactSSII?: string,
        public contactClient?: string,
        public dateDemarrage?: any,
        public commentaires?: string,
        public etat?: Statut,
        public consultant?: BaseEntity,
        public bizDev?: BaseEntity,
    ) {
    }
}
