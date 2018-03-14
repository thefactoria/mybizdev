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
        public etat?: Statut,
        public consultants?: BaseEntity[],
        public bizDev?: BaseEntity,
    ) {
    }
}
