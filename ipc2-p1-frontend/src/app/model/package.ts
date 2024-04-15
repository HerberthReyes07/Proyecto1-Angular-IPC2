import { PackageStatus } from "./package-status";

export class Package {
    id!: number;
    customerId!: number;
    destinationId!: number;
    parameterId!: number;
    weight!: number;
    shippingCost!: number;
    status!: PackageStatus;
    invoiceNo!: number;
    entryDate!: string;
}
